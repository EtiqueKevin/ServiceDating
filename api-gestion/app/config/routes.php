<?php
declare(strict_types=1);

use Slim\App;

return function( App $app): App {

    $app->get('/besoins[/]', GetBesoinsAdminAction::class);
    $app->get('/users/besoins[/]', GetBesoinsByUserAction::class);
    $app->post('/besoins[/]', PostBesoinsAction::class);
    $app->put('/besoins/{id}[/]', GetBesoinsByIdAction::class);
    $app->post('/salaries[/]', PostSalariesAction::class);
    $app->get('/salaries[/]', GetSalariesAction::class);
    $app->get('/competences[/]', GetCompetencesAction::class);
    $app->get('/competences/{id}[/]', GetCompetencesByIdAction::class);
    $app->post('/competences[/]', PostCompetencesAction::class);
    $app->put('/competences/{id}[/]', PutCompetencesAction::class);
    $app->post('/affectations[/]', PostAffectationsAction::class);


    return $app;
};