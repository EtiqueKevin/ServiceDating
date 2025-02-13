<?php
declare(strict_types=1);

use gestion\application\actions\GetBesoinsAdminAction;
use gestion\application\actions\GetBesoinsByUserAction;
use gestion\application\actions\GetCompetencesByClientAction;
use gestion\application\actions\PostBesoinAction;
use gestion\application\actions\PostUtilisateurAction;
use gestion\application\actions\PutBesoinByIdAction;
use gestion\application\actions\GetSalariesAction;
use gestion\application\actions\GetCompetencesAction;
use gestion\application\actions\GetCompetencesByIdAction;
use gestion\application\actions\PostCompetencesAction;
use gestion\application\actions\PutCompetencesAction;
use gestion\application\actions\DeleteCompetencesAction;

use gestion\application\middlewares\AuthMiddleware;
use gestion\application\middlewares\AuthzMiddleware;
use Slim\App;

return function( App $app): App {

    $app->get('/besoins[/]', GetBesoinsAdminAction::class)
        ->add(AuthzMiddleware::class)
        ->setName('GetBesoinsAdmin') ;

    $app->get('/users/besoins[/]', GetBesoinsByUserAction::class)
        ->add(AuthMiddleware::class)
        ->setName("GetBesoinsByUser");

    $app->post('/besoins[/]', PostBesoinAction::class)
        ->add(AuthMiddleware::class)
        ->setName('PostBesoin');

    $app->put('/besoins/{id}[/]', PutBesoinByIdAction::class)
        ->add(AuthMiddleware::class)
        ->setName('PutBesoinById');

    $app->get('/salaries[/]', GetSalariesAction::class)
        ->add(AuthzMiddleware::class)
        ->setName('GetSalaries');
    $app->get('/competences/clients/[/]', GetCompetencesByClientAction::class)
        ->setName('GetCompetencesByClient');

    $app->get('/competences[/]', GetCompetencesAction::class)
        ->setName('GetCompetences');

    $app->get('/competences/{id}[/]', GetCompetencesByIdAction::class)
    ->setName('GetCompetencesById');

    $app->post('/competences[/]', PostCompetencesAction::class)
        ->add(AuthzMiddleware::class)
        ->setName('PostCompetences');

    $app->put('/competences/{id}[/]', PutCompetencesAction::class)
        ->add(AuthzMiddleware::class)
        ->setName('PutCompetences');

    $app->delete('/competences/{id}[/]', DeleteCompetencesAction::class)
        ->add(AuthzMiddleware::class)
        ->setName('DeleteCompetences');

    $app->post('/utilisateur[/]',PostUtilisateurAction::class)
        ->setName('PostUtilisateur');

    $app->get('/competences/clients/{id}[/]', GetCompetencesByClientAction::class)
        ->setName('GetCompetencesByClient');
    return $app;
};