<?php

namespace apiAuth\core\domain\entities\user;

use apiAuth\core\domain\entities\Entity;
use apiAuth\core\dto\user\UserDTO;

class User extends Entity
{
    protected string $email;
    protected int $role;
    protected string $password;
    protected ?string $name;
    protected ?string $surname;
    protected ?string $phone;

    public function __construct(string $email, string $password, int $role, ?string $name = null, ?string $surname = null, ?string $phone = null)
    {
        $this->email = $email;
        $this->password = $password;
        $this->role = $role;
        $this->name = $name;
        $this->surname = $surname;
        $this->phone = $phone;
    }

    public function toDTO(): UserDTO
    {
        return new UserDTO($this->ID, $this->email, $this->role);
    }

}