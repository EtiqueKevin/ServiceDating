<?php

namespace gestion\core\services;

use gestion\core\domain\entities\Utilisateur;
use gestion\core\dto\AuthUserDTO;
use gestion\core\dto\BesoinDTO;
use gestion\core\dto\InputBesoinDTO;
use gestion\core\dto\InputCompetenceSalarie;
use gestion\core\dto\InputPutBesoinDTO;
use gestion\core\dto\InputUtilisateurDTO;
use gestion\core\repositoryInterface\AuthRepositoryInterface;
use gestion\core\repositoryInterface\GestionRepositoryInterface;
use PHPUnit\Exception;

class GestionService implements GestionServiceInterface
{

    private GestionRepositoryInterface $gestionRepository;

    private AuthRepositoryInterface $authRepository;

    public function __construct(GestionRepositoryInterface $gestionRepository, AuthRepositoryInterface $authRepository)
    {
        $this->gestionRepository = $gestionRepository;
        $this->authRepository = $authRepository;
    }

    public function getBesoinsAdmin(): array
    {
        try {
            $besoins  = $this->gestionRepository->getBesoinsAdmin();
            $besoinsDTO = [];
            foreach ($besoins as $besoin) {
                $besoinsDTO[] = $besoin->toDTO();
            }
            return $besoinsDTO;
        }catch (Exception $e) {
            throw new GestionServiceException($e->getMessage());
        }
    }

    public function getBesoinsByUser(string $id): array
    {
        try {
            $besoins = $this->gestionRepository->getBesoinsByUser($id);
            $besoinsDTO = [];
            foreach ($besoins as $besoin) {
                $besoinsDTO[] = $besoin->toDTO();
            }
            return $besoinsDTO;
        }catch (Exception $e) {
            throw new GestionServiceException($e->getMessage());
        }
    }

    public function creerBesoin(InputBesoinDTO $besoin): BesoinDTO
    {
        try {
            $besoinEntity = $this->gestionRepository->creerBesoin();
            return $besoinEntity->toDTO();
        }catch (Exception $e) {
            throw new GestionServiceException($e->getMessage());
        }
    }

    public function createUtilisateur(InputUtilisateurDTO $iud):void{
        $utilisateur = new Utilisateur($iud->name,$iud->surname,$iud->phone);
        $utilisateur->setID($iud->id);
        $this->gestionRepository->saveUtilisateur($utilisateur);
    }

    public function createAuth(AuthUserDTO $auth):string{
        $id = $this->authRepository->CreationUserReturnID($auth);
        return $id;
    }

    public function associationSalarieCompetence(InputCompetenceSalarie $inputCompetenceSalarie):void{
        $this->gestionRepository->associationSalarieCompetence($inputCompetenceSalarie->idSalarie,$inputCompetenceSalarie->tabIdCompetences);
    }

    public function modifierBesoin(InputPutBesoinDTO $inputPutBesoinDTO): BesoinDTO
    {
        try {
            $besoinEntity = $this->gestionRepository->modifierBesoin($inputPutBesoinDTO->id_besoin, $inputPutBesoinDTO->id_user, $inputPutBesoinDTO->competence_id, $inputPutBesoinDTO->description);
            return $besoinEntity->toDTO();
        }catch (Exception $e) {
            throw new GestionServiceException($e->getMessage());
        }
    }
}