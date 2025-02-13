<?php

namespace gestion\application\actions;

use Exception;
use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PostUtilisateurAction extends AbstractAction
{
    private GestionServiceInterface $service;

    public function __construct( GestionServiceInterface $service)
    {
        $this->service = $service;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $params = $rq->getParsedBody() ?? null;


        try{
            $this->service->createClient(new InputUtilisateurDTO($params['id'],$params['name'],$params['surname'],$params['linkpic'], $params['pseudo']));
        }catch (Exception $e){
            throw new HttpBadRequestException($rq, $e->getMessage());
        }
        return $rs->withStatus(201);

    }
}