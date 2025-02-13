<?php

namespace gestion\infrastructure\services;

use Exception;
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

    public function recuperationMailPlayer(string $iduser): string {
        try {
            $response = $this->client->get('/users/mail', [
                'query' => ['userID' => $iduser]
            ]);
        }catch (Exception $e) {
            throw new Exception("Erreur lors de la récupération du mail");
        }

        $data = json_decode($response->getBody()->getContents(), true);
        return $data["mail"];
    }
}