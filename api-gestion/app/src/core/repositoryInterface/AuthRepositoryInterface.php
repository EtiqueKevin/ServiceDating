<?php

namespace gestion\core\repositoryInterface;

use gestion\core\dto\AuthUserDTO;

interface AuthRepositoryInterface
{
    public function RecuperationIDUser(string $token): string;
    public function CreationUserReturnID(AuthUserDTO $aud) : string;
    public function RecuperationRoleUser(string $token): string;
    public function getUsersByRoles(string $role): array;
}