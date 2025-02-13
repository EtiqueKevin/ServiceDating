<?php

namespace gestion\infrastructure\repository;

use gestion\core\repositoryInterface\GestionRepositoryInterface;
use PDO;

class PDOGestionRepository implements GestionRepositoryInterface
{
    private PDO $pdo;

    public function __construct(PDO $pdo)
    {
        $this->pdo = $pdo;
    }

    public function getBesoinsAdmin(): array
    {
        $stmt = $this->pdo->query('SELECT * FROM besoins');
        $data = $stmt->fetchAll();

        $besoins = [];
        foreach ($data as $besoin) {
            $besoins[] = new BesoinDTO();
        }

        return $besoins;
    }
}