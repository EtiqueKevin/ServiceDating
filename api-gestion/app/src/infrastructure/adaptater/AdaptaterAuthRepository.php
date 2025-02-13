<?php

namespace gestion\infrastructure\services;

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
        $response = $this->client->get('/token/user/id', [
            'headers' => ['Authorization' => 'Bearer '.$token]
        ]);
        $data = json_decode($response->getBody()->getContents(), true);
        return $data["userID"];
    }

    public function CreationUserReturnID(AuthUserDTO $aud) : string{

        $response = $this->client->get('/token/user/id', [
            'auth' => [$aud->email, $aud->password]
        ]);

        $data = json_decode($response->getBody()->getContents(), true);
        return $data["userID"];
    }
}