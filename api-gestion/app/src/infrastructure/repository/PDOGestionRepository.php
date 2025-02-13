<?php

namespace gestion\infrastructure\repository;

use gestion\core\domain\entities\Besoin;
use gestion\core\domain\entities\Competence;
use gestion\core\domain\entities\Utilisateur;
use gestion\core\repositoryInterface\GestionRepositoryException;
use gestion\core\repositoryInterface\GestionRepositoryInterface;
use gestion\core\repositoryInterface\GestionRepositoryNotFoundException;
use PDO;
use PHPUnit\Exception;

class PDOGestionRepository implements GestionRepositoryInterface
{
    private PDO $pdo;

    public function __construct(PDO $pdo)
    {
        $this->pdo = $pdo;
    }

    public function getBesoinsAdmin(): array
    {
        try {
            $stmt = $this->pdo->query('SELECT * FROM besoins');
            $data = $stmt->fetchAll();
        }catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucun besoin trouvé');
        }

        try {
            $besoins = [];
            foreach ($data as $besoin) {
                $utilisateur = $this->getUserById($besoin['client_id']);
                $competence = $this->getCompetenceById($besoin['competence_id']);
                $besoinEntity = new Besoin($utilisateur, $competence, $besoin['description'], $besoin['status'], $besoin['date_demande']);
                $besoinEntity->setId($besoin['id']);
                $besoins[] = $besoinEntity;
            }
            return $besoins;
        }catch (Exception) {
            throw new GestionRepositoryException('Erreur lors de la récupération des besoins');
        }
    }

    public function getUserById(string $id): Utilisateur
    {
        try {
            $stmt = $this->pdo->prepare('SELECT * FROM utilisateurs WHERE id = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();
            $data = $stmt->fetch();

        }catch(\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucun utilisateur trouvé');
        }

        try {
            $user = new Utilisateur($data['nom'], $data['prenom'], $data['phone']);
            $user->setId($data['id']);
            return $user;
        }catch (Exception) {
            throw new GestionRepositoryException('Erreur lors de la récupération de l\'utilisateur');
        }
    }

    public function getCompetenceById(string $id): Competence
    {
        try {
            $stmt = $this->pdo->prepare('SELECT * FROM competences WHERE id = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();
            $data = $stmt->fetch();
        }catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucune compétence trouvée');
        }

        return new Competence($data['nom'], $data['description']);
    }

    public function getBesoinsByUser(string $id): array
    {
        try {
            $stmt = $this->pdo->prepare('SELECT * FROM besoins WHERE client_id = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();
            $data = $stmt->fetchAll();
        }catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucun besoin trouvé');
        }

        try {
            $besoins = [];
            foreach ($data as $besoin) {
                $utilisateur = $this->getUserById($besoin['client_id']);
                $competence = $this->getCompetenceById($besoin['competence_id']);
                $besoinEntity = new Besoin($utilisateur, $competence, $besoin['description'], $besoin['status'], $besoin['date_demande']);
                $besoinEntity->setId($besoin['id']);
                $besoins[] = $besoinEntity;
            }
            return $besoins;
        }catch (Exception) {
            throw new GestionRepositoryException('Erreur lors de la récupération des besoins');
        }
    }
}