<?php

namespace apiAuth\application\actions;

use apiAuth\core\dto\user\InputUserDTO;
use apiAuth\core\services\user\UserServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class RegisterSalarieAction extends AbstractAction
{
    private UserServiceInterface $userService;

    public function __construct(UserServiceInterface $userService){
        $this->userService = $userService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {

        $params = $rq->getParsedBody();
        $email = $params['email'];
        $mdp =  $params['mdp'];


        $id = $this->userService->createSalarier(new InputUserDTO($email, $mdp));

        $response = [
            'type' => 'ressource',
            'userID' => $id
        ];

        $rs->getBody()->write(json_encode($response));

        return $rs->withStatus(201)->withHeader('Content-Type', 'application/json');


    }
}