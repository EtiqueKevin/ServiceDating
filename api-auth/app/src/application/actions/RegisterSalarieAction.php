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
        $authHeader = $rq->getHeaderLine('Authorization');
        $authHeaderTab = explode(' ', $authHeader);
        if ($authHeaderTab[0] !== 'Basic') {
            throw new HttpUnauthorizedException($rq, 'Authorization header absent ou mal formé');
        }
        $encodedCredentials = $authHeaderTab[1];
        $decodedCredentials = base64_decode($encodedCredentials);
        $credentials = explode(':', $decodedCredentials);

        $email = filter_var($credentials[0], FILTER_SANITIZE_EMAIL);
        $mdp = $credentials[1];


        $id = $this->userService->createSalarier(new InputUserDTO($email, $mdp));

        $response = [
            'type' => 'ressource',
            'userID' => $id
        ];

        $rs->getBody()->write(json_encode($response));

        return $rs->withStatus(201)->withHeader('Content-Type', 'application/json');


    }
}