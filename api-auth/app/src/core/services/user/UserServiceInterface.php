<?php

namespace apiAuth\core\services\user;

use apiAuth\core\dto\user\InputUserDTO;
use apiAuth\core\dto\user\UserDTO;

interface UserServiceInterface
{
    public function findUserById(string $ID): UserDTO;
    public function createUser(InputUserDTO $input): void;

    public function createSalarier(InputUserDTO $input): string;
    public function getusersByRole(string $role): array;
}