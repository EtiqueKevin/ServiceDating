<?php

namespace gestion\core\services;

use gestion\core\repositoryInterface\GestionRepositoryInterface;

class GestionService implements GestionServiceInterface
{

    private GestionRepositoryInterface $gestionRepository;

    public function __construct(GestionRepositoryInterface $gestionRepository)
    {
        $this->gestionRepository = $gestionRepository;
    }

    public function getBesoinsAdmin(): array
    {
        $besoins  = $this->gestionRepository->getBesoinsAdmin();
        $besoinsDTO = [];
        foreach ($besoins as $besoin) {
            $besoinsDTO[] = $besoin->toDTO();
        }
        return $besoinsDTO;
    }
}