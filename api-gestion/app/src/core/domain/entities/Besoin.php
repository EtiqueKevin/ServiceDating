<?php

namespace gestion\core\domain\entities;

use gestion\core\dto\BesoinDTO;

class Besoin extends Entity
{
    private Utilisateur $client;
    private Competence $competence;
    private string $description;
    private int $status;
    private string $date_demande;

    public function __construct(Utilisateur $client, Competence $competence, string $description, int $status, string $date_demande)
    {
        $this->client = $client;
        $this->competence = $competence;
        $this->description = $description;
        $this->status = $status;
        $this->date_demande = $date_demande;
    }

    public function toDTO(): BesoinDTO
    {
        return new BesoinDTO(
            $this->id,
            $this->client->toDTO(),
            $this->competence->toDTO(),
            $this->description,
            $this->status,
            $this->date_demande
        );
    }
}