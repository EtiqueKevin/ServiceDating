<?php

namespace gestion\core\dto;

class InputPutBesoinDTO extends DTO
{
    private string $id_besoin;
    private string $id_user;
    private string $competence_id;
    private string $description;


    public function __construct(string $id_besoin, string $id_user, string $competence_id, string $description)
    {
        $this->id_besoin = $id_besoin;
        $this->id_user = $id_user;
        $this->competence_id = $competence_id;
        $this->description = $description;
    }

    public function getIdBesoin(): string
    {
        return $this->id_besoin;
    }

    public function getIdUser(): string
    {
        return $this->id_user;
    }

    public function getCompetenceId(): string
    {
        return $this->competence_id;
    }

    public function getDescription(): string
    {
        return $this->description;
    }


}