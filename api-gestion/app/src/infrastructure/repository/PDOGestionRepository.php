<?php

namespace gestion\infrastructure\repository;

use gestion\core\domain\entities\Besoin;
use gestion\core\domain\entities\Competence;
use gestion\core\domain\entities\Utilisateur;
use gestion\core\dto\InputCompetenceSalarie;
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
                $besoinEntity = new Besoin($utilisateur, $competence, $besoin['description'], $besoin['status'], $besoin['date_init_besoin']);
                $besoinEntity->setId($besoin['besoin_id']);
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
            $stmt = $this->pdo->prepare('SELECT * FROM utilisateurs WHERE utilisateur_id = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();
            $data = $stmt->fetch();

        }catch(\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucun utilisateur trouvé.');
        }

        try {
            $user = new Utilisateur($data['name'], $data['surname'], $data['phone']);
            $user->setId($data['utilisateur_id']);
            return $user;
        }catch (Exception) {
            throw new GestionRepositoryException('Erreur lors de la récupération de l\'utilisateur');
        }
    }

    public function getCompetenceById(string $id): Competence
    {
        try {
            $stmt = $this->pdo->prepare('SELECT * FROM competences WHERE competence_id = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();
            $data = $stmt->fetch();
        }catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucune compétence trouvée');
        }

        $competence = new Competence($data['name'], $data['description']);
        $competence->setId($data['competence_id']);
        return $competence;
    }

    public function getCompetences(): array
    {
        try {
            $stmt = $this->pdo->query('SELECT * FROM competences');
            $data = $stmt->fetchAll();
        }catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucune compétence trouvée');
        }

        try {
            $competences = [];
            foreach ($data as $competence) {
                $competenceEntity = new Competence($competence['name'], $competence['description']);
                $competenceEntity->setId($competence['competence_id']);
                $competences[] = $competenceEntity;
            }
            return $competences;
        }catch (Exception) {
            throw new GestionRepositoryException('Erreur lors de la récupération des compétences');
        }
    }

    public function saveCompetence(Competence $competence):void{
        $name = $competence->getNom();
        $description = $competence->getDescription();

        $stmt = $this->pdo->prepare('INSERT INTO "competences" ("name","description" ) VALUES ( ?, ?)');

        $stmt->bindParam(1, $name);
        $stmt->bindParam(2, $description);
        $stmt->execute();
    }

    public function updateCompetence(Competence $competence):void{
        $name = $competence->getNom();
        $description = $competence->getDescription();
        $id = $competence->getID();

        $stmt = $this->pdo->prepare('UPDATE "competences" SET "name" = ?, "description" = ? WHERE "competence_id" = ?');

        $stmt->bindParam(1, $name);
        $stmt->bindParam(2, $description);
        $stmt->bindParam(3, $id);
        $stmt->execute();
    }

    public function deleteCompetence(string $id): void {
        try {
            $stmt = $this->pdo->prepare('DELETE FROM "salaries_competences" WHERE "competence_id" = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();

            $stmt = $this->pdo->prepare('DELETE FROM "besoins" WHERE "competence_id" = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();

            $stmt = $this->pdo->prepare('DELETE FROM "competences" WHERE "competence_id" = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();
        }catch (\Exception $e) {
            throw new GestionRepositoryException('Erreur lors de la suppression de la compétence');
        }
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
                $besoinEntity = new Besoin($utilisateur, $competence, $besoin['description'], $besoin['status'], $besoin['date_init_besoin']);
                $besoinEntity->setId($besoin['besoin_id']);
                $besoins[] = $besoinEntity;
            }
            return $besoins;
        }catch (Exception) {
            throw new GestionRepositoryException('Erreur lors de la récupération des besoins');
        }
    }

    public function getBesoinsByUserWithPagination(string $id, int $page, int $limit): array
    {
        try {
            $offset = ($page - 1) * $limit;

            $stmt = $this->pdo->prepare('SELECT * FROM besoins WHERE client_id = ? LIMIT ? OFFSET ?');
            $stmt->bindParam(1, $id);
            $stmt->bindParam(2, $limit);
            $stmt->bindParam(3, $offset);
            $stmt->execute();
            $data = $stmt->fetchAll();

        } catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucun besoin trouvé');
        }

        try {
            $besoins = [];
            foreach ($data as $besoin) {
                $utilisateur = $this->getUserById($besoin['client_id']);
                $competence = $this->getCompetenceById($besoin['competence_id']);
                $besoinEntity = new Besoin($utilisateur, $competence, $besoin['description'], $besoin['status'], $besoin['date_init_besoin']);
                $besoinEntity->setId($besoin['besoin_id']);
                $besoins[] = $besoinEntity;
            }
            return $besoins;
        } catch (Exception) {
            throw new GestionRepositoryException('Erreur lors de la récupération des besoins');
        }
    }


    public function creerBesoin(string $id, string $competence_id, string $description): Besoin
    {
        try {
            $date = date('Y-m-d H:i:s');
            $status = 0;
            $stmt = $this->pdo->prepare('INSERT INTO besoins (client_id, competence_id, description, status, date_init_besoin) VALUES (?, ?, ?, ?, ?) RETURNING besoin_id');
            $stmt->bindParam(1, $id);
            $stmt->bindParam(2, $competence_id);
            $stmt->bindParam(3, $description);
            $stmt->bindParam(4, $status, PDO::PARAM_INT);
            $stmt->bindParam(5, $date);
            $stmt->execute();
            $data = $stmt->fetch();

        }catch (\Exception $e) {
            throw new GestionRepositoryException('Erreur lors de la création du besoin');
        }

        return $this->getBesoinsById($data['besoin_id']);
    }

    public function getBesoinsById(string $id): Besoin
    {
        try {
            $stmt = $this->pdo->prepare('SELECT * FROM besoins WHERE besoin_id = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();
            $data = $stmt->fetch();
        }catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucun besoin trouvé');
        }

        try {
            $utilisateur = $this->getUserById($data['client_id']);
            $competence = $this->getCompetenceById($data['competence_id']);
            $besoinEntity = new Besoin($utilisateur, $competence, $data['description'], $data['status'], $data['date_init_besoin']);
            $besoinEntity->setId($data['besoin_id']);
            return $besoinEntity;
        }catch (Exception) {
            throw new GestionRepositoryException('Erreur lors de la récupération du besoin');
        }
    }

    public function saveUtilisateur(Utilisateur $utilisateur):void{
        $name = $utilisateur->name;
        $surname = $utilisateur->surname;
        $phone = $utilisateur->phone;
        $id = $utilisateur->getID();

        $stmt = $this->pdo->prepare('INSERT INTO "utilisateurs" ("utilisateur_id","name","surname","phone" ) VALUES (?, ?, ?, ?)');

        $stmt->bindParam(1, $id);
        $stmt->bindParam(2, $name);
        $stmt->bindParam(3, $surname);
        $stmt->bindParam(4, $phone);
        $stmt->execute();
    }

    public function associationSalarieCompetence( string $idS, array $tabC):void{
        $idSalarie = $idS;
        $tab = $tabC;

        foreach ($tab as $competence) {
            $idCompetence = $competence['id'];
            $interest = $competence['interet'];
            try{
                $stmt = $this->pdo->prepare('INSERT INTO "salaries_competences" ("salarie_id","competence_id","interest" ) VALUES (?, ?, ?)');

                $stmt->bindParam(1, $idSalarie);
                $stmt->bindParam(2, $idCompetence);
                $stmt->bindParam(3, $interest);
                $stmt->execute();
            }catch (\Exception $e){
                throw new GestionRepositoryException($e->getMessage());
            }

        }
    }

    public function modifierBesoin(string $id_besoin, string $id_user, string $competence_id, string $description): Besoin
    {
        try {
            $stmt = $this->pdo->prepare('UPDATE besoins SET client_id = ?, competence_id = ?, description = ? WHERE besoin_id = ?');
            $stmt->bindParam(1, $id_user);
            $stmt->bindParam(2, $competence_id);
            $stmt->bindParam(3, $description);
            $stmt->bindParam(4, $id_besoin);
            $stmt->execute();
        }catch (\Exception $e) {
            throw new GestionRepositoryException('Erreur lors de la modification du besoin');
        }

        return $this->getBesoinsById($id_besoin);
    }

    public function getSalaries(array $idUsers): array
    {
        $salaries = [];
        foreach ($idUsers as $idUser) {
            $stmt = $this->pdo->prepare('SELECT * FROM utilisateurs WHERE utilisateur_id = ?');
            $stmt->bindParam(1, $idUser);
            $stmt->execute();
            $data = $stmt->fetch();
            $salarie = new Utilisateur($data['name'], $data['surname'], $data['phone']);
            $salarie->setId($data['utilisateur_id']);
            $salaries[] = $salarie;
        }
        return $salaries;
    }

    public function getCompetencesBySalarie(string $id): array
    {
        try {
            $stmt = $this->pdo->prepare('SELECT * FROM salaries_competences WHERE salarie_id = ?');
            $stmt->bindParam(1, $id);
            $stmt->execute();
            $data = $stmt->fetchAll();
        }catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucune compétence trouvée');
        }

        $competences = [];
        foreach ($data as $competence) {
            $competences[] = [
                'id' => $competence['competence_id'],
                'interet' => $competence['interest']
            ];
        }
        return $competences;
    }

    public function getCompetencesByClient(array $clients): array
    {
        try {
            $competencesById = [];
            foreach ($clients as $client) {
                $stmt = $this->pdo->prepare('SELECT DISTINCT * FROM besoins WHERE client_id = ?');
                $stmt->bindParam(1, $client);
                $stmt->execute();
                $data = $stmt->fetchAll();
                $competences = [];
                foreach ($data as $besoin) {
                    $competence = $this->getCompetenceById($besoin['competence_id']);
                    $competences[] = $competence;
                    $competencesById [] = [
                        "id_user" => $client,
                        "competences" => $competences
                    ];
                }
            }
            return $competencesById;
        }catch (\Exception $e) {
            throw new GestionRepositoryNotFoundException('Aucune compétence trouvée');
        }
    }
}