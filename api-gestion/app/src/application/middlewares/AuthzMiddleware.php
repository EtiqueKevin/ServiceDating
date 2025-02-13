<?php

namespace gestion\application\middlewares;

use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use Psr\Http\Server\RequestHandlerInterface;
use Slim\Exception\HttpUnauthorizedException;
use Slim\Routing\RouteContext;

class AuthzMiddleware
{

    private GestionServiceInterface $gestion;

    public function __construct(GestionServiceInterface $gestion){
        $this->gestion = $gestion;
    }

    public function __invoke(ServerRequestInterface $rq, RequestHandlerInterface $next): ResponseInterface{

        $routeContext = RouteContext::fromRequest($rq);
        $route = $routeContext->getRoute();
        $routeName = $route->getName();

        $h = $rq->getHeader('Authorization')[0];
        $tokenstring = sscanf($h, "Bearer %s")[0];

        // Vérification des droits d'accès selon la route
        switch ($routeName) {
            case 'rdvsId':
                if (!$this->gestion->adminVerification($tokenstring)) {
                    throw new HttpUnauthorizedException($rq, 'Vous n\'avez pas les droits pour accéder à cette ressource');
                }
                break;
            case 'praticiensPlanning':
                $idPraticien = $route->getArgument('id');
                if (!$this->gestion->authorisePlanningAccess($userId, $idPraticien)) {
                    throw new HttpUnauthorizedException($rq, 'Vous n\'avez pas les droits pour accéder à cette ressource');
                }
                break;
            case 'rdvsAdd':
                $body = $rq->getParsedBody();
                $idPatient = $body['idPatient'] ?? null;
                $idPraticien = $body['idPraticien'] ?? null;
                if (!$this->gestion->authoriseCreateRDV($userId, $idPatient, $idPraticien)) {
                    throw new HttpUnauthorizedException($rq, 'Vous n\'avez pas les droits pour créer cette ressource');
                }
                break;
            default:
                throw new HttpUnauthorizedException($rq, 'Route non autorisée');
        }

        $response = $next->handle($rq);
        return $response;
    }


}