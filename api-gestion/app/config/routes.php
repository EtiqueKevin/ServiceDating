<?php
declare(strict_types=1);

use gestion\application\actions\GetBesoinsAdminAction;
use gestion\application\actions\GetBesoinsByUserAction;
use gestion\application\actions\PostBesoinAction;
use gestion\application\actions\PutBesoinByIdAction;
use Slim\App;

return function( App $app): App {

    $app->get('/besoins[/]', GetBesoinsAdminAction::class);
    $app->get('/users/besoins[/]', GetBesoinsByUserAction::class);
    $app->post('/besoins[/]', PostBesoinAction::class);
    $app->put('/besoins/{id}[/]', PutBesoinByIdAction::class);
    $app->post('/salaries[/]', PostSalariesAction::class);
    $app->get('/salaries[/]', GetSalariesAction::class);
    $app->get('/competences[/]', GetCompetencesAction::class);
    $app->get('/competences/{id}[/]', GetCompetencesByIdAction::class);
    $app->post('/competences[/]', PostCompetencesAction::class);
    $app->put('/competences/{id}[/]', PutCompetencesAction::class);
    $app->delete('/competences/{id}[/]', DeleteCompetencesAction::class);

    return $app;
};