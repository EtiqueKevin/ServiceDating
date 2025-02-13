<?php

namespace apiAuth\infrastructure\adaptater;

use apiAuth\core\domain\entities\user\User;
use apiAuth\core\repositoryInterface\GestionRepositoryInterface;

class AdaptaterGestionRepository implements GestionRepositoryInterface
{
    private $client;

    public function __construct($client)
    {
        $this->client = $client;
    }
    public function createUtilisateur(User $user): void
    {
        $this->client->post('/utilisateur', [
            'json' => ['id' => $user->getID(), 'name' => $user->name, 'surname' => $user->surname, 'phone' => $user->phone],
        ]);
    }
}