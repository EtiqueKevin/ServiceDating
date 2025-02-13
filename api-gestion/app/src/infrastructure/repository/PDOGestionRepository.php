<?php

namespace gestion\infrastructure\repository;

use gestion\core\domain\entities\Besoin;
use gestion\core\domain\entities\Competence;
use gestion\core\domain\entities\Utilisateur;
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
            $utilisateur = $this->getUserById($besoin['client_id']);
            $competence = $this->getCompetenceById($besoin['competence_id']);
            $besoinEntity = new Besoin($utilisateur, $competence, $besoin['description'], $besoin['status'], $besoin['date_demande']);
            $besoinEntity->setId($besoin['id']);
            $besoins[] = $besoinEntity;
        }
        return $besoins;
    }

    public function getUserById(string $id): Utilisateur
    {
        $stmt = $this->pdo->prepare('SELECT * FROM utilisateurs WHERE id = ?');
        $stmt->bindParam(1, $id);
        $stmt->execute();
        $data = $stmt->fetch();

        $user = new Utilisateur($data['nom'], $data['prenom'], $data['phone']);
        $user->setId($data['id']);
        return $user;
    }

    public function getCompetenceById(string $id): Competence
    {
        $stmt = $this->pdo->prepare('SELECT * FROM competences WHERE id = ?');
        $stmt->bindParam(1, $id);
        $stmt->execute();
        $data = $stmt->fetch();

        return new Competence($data['nom'], $data['description']);
    }
}