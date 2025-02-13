<?php

namespace apiAuth\core\dto\user;

use apiAuth\core\dto\DTO;

class InputUserDTO extends DTO
{
    protected string $email;
    protected string $password;
    protected ?string $name;
    protected ?string $surname;
    protected ?string $phone;

    public function __construct(string $email, string $password, ?string $name = null, ?string $surname = null, ?string $phone = null) {
        $this->email = $email;
        $this->password = $password;
        $this->name = $name;
        $this->surname = $surname;
        $this->phone = $phone;
    }
}