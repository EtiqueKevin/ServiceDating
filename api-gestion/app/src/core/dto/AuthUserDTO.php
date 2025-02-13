<?php

namespace gestion\core\dto;

class AuthUserDTO extends DTO
{
    protected string $email;
    protected string $password;

    public function __construct(string $email, string $password){
        $this->email = $email;
        $this->password = $password;
    }

}