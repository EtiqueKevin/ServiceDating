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
        return new Client(['base_uri' => 'http://api.optimisation.servicedating:46247']);
    },

    'client_gestion' => function (ContainerInterface $c){
        return new Client(['base_uri' => 'http://api.gestion.servicedating:80']);
    },

    'client_auth' => function (ContainerInterface $c){
        return new Client(['base_uri' => 'http://api.auth.servicedating:80']);
    },

    GeneriqueOptimisationAction::class => function (ContainerInterface $c){
        return new GeneriqueOptimisationAction($c->get('client_optimisation'));
    },

    GeneriqueGestionAction::class => function (ContainerInterface $c){
        return new GeneriqueGestionAction($c->get('client_gestion'));
    },

    GeneriqueAuthAction::class => function (ContainerInterface $c){
        return new GeneriqueAuthAction($c->get('client_auth'));
    },
];