<?php

namespace apiAuth\application\actions;

use apiAuth\application\providers\auth\AuthProviderInterface;
use apiAuth\core\services\user\UserServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use Slim\Exception\HttpBadRequestException;

class GetUserByRoleAction extends AbstractAction
{
    private UserServiceInterface $userService;

    public function __construct(UserServiceInterface $userService)
    {
        $this->userService = $userService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $role = $rq->getQueryParams()['role'] ?? null;

        if($role === null) {
            throw new HttpBadRequestException($rq, "parametre dans query role manquant");
        }

        try {
            $roleRes = $this->userService->getusersByRole($role);

        }catch (\Exception $e){
            throw new HttpBadRequestException($rq,"erreur lors de la récupération de l'id" . $e->getMessage());
        }

        $response = [
            'type' => 'ressource',
            'roles' => $roleRes,
        ];

        $rs->getBody()->write(json_encode($response));

        return $rs->withStatus(200)->withHeader('Content-Type', 'application/json');


    }
}