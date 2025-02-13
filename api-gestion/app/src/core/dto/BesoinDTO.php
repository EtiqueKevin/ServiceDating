<?php

namespace gestion\core\dto;

class BesoinDTO extends DTO
{
    private string $id;
    private UtilisateurDTO $client;
    private CompetenceDTO $competence;
    private string $description;
    private int $status;
    private string $date_demande;

    public function __construct(string $id, UtilisateurDTO $client, CompetenceDTO $competence, string $description, int $status, string $date_demande) {
        $this->id = $id;
        $this->client = $client;
        $this->competence = $competence;
        $this->description = $description;
        $this->status = $status;
        $this->date_demande = $date_demande;
    }

    public function jsonSerialize(): array
    {
        return [
            'id' => $this->id,
            'client' => $this->client,
            'competence' => $this->competence,
            'description' => $this->description,
            'status' => $this->status,
            'date_demande' => $this->date_demande
        ];
    }
}