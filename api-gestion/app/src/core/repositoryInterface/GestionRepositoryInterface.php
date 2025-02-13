<?php

namespace gestion\core\repositoryInterface;

interface GestionRepositoryInterface
{
    public function getBesoinsAdmin(): array;
    public function getBesoinsByUser(string $id): array;
}