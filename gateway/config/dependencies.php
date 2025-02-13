<?php

use gateway\application\actions\GeneriqueOptimisationAction;
use gateway\application\actions\GeneriqueAuthAction;
use gateway\application\actions\GeneriqueGestionAction;
use gateway\application\middleware\AuthMiddleware;
use GuzzleHttp\Client;
use Psr\Container\ContainerInterface;
use Psr\Http\Client\ClientInterface;
return [

    'client_optimisation' => function (ContainerInterface $c){
        return new Client(['base_uri' => 'http://api.optimisation:80']);
    },

    'client_gestion' => function (ContainerInterface $c){
        return new Client(['base_uri' => 'http://api.gestion:80']);
    },

    'client_auth' => function (ContainerInterface $c){
        return new Client(['base_uri' => 'http://api.auth:80']);
    },
];