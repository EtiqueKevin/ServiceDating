<?php

namespace gestion\core\repositoryInterface;

interface AuthRepositoryInterface
{
    public function RecuperationIDUser(string $token): string;
    public function recuperationMailPlayer(string $iduser): string;
}