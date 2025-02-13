<?php

namespace gestion\infrastructure\adaptater;

use Exception;
use gestion\core\dto\AuthUserDTO;
use gestion\core\repositoryInterface\AuthRepositoryInterface;

class AdaptaterAuthRepository implements AuthRepositoryInterface
{
    private $client;

    public function __construct($client)
    {
        $this->client = $client;
    }

    public function RecuperationIDUser(string $token): string
    {
        $response = $this->client->post('/token/user/id', [
            'headers' => ['Authorization' => 'Bearer '.$token]
        ]);
        $data = json_decode($response->getBody()->getContents(), true);
        return $data["userID"];
    }

    public function CreationUserReturnID(AuthUserDTO $aud) : string{
        $response = $this->client->post('/register/salarie', [
            'json' => ["email"=>$aud->email,"mdp"=> $aud->password]
        ]);

        $data = json_decode($response->getBody()->getContents(), true);

        return $data["userID"];
    }

    public function RecuperationRoleUser(string $token): string
    {
        $response = $this->client->post('/token/user/role', [
            'headers' => ['Authorization' => 'Bearer '.$token]
        ]);

        $data = json_decode($response->getBody()->getContents(), true);

        return $data["role"];
    }
}