<?php

namespace gestion\core\services;

interface GestionServiceInterface
{
    public function getBesoinsAdmin(): array;
    public function getBesoinsByUser(string $id): array;
}