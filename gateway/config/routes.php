<?php
declare(strict_types=1);

use gateway\application\actions\GeneriqueOptimisationAction;
use gateway\application\actions\GeneriqueAuthAction;
use gateway\application\actions\GeneriqueGestionAction;
use gateway\application\middleware\AuthMiddleware;
use gateway\application\middleware\Cors;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Slim\App;

return function(App $app): App {

    $app->add(Cors::class);

    $app->options('/{routes:.+}', function(Request $rq, Response $rs, array $args): Response {
        return $rs;
    });

    /*************************
    * Routes de l'API optimisation
    *************************/

    $app->post('/affectations[/]', GeneriqueOptimisationAction::class);
    $app->post('/affectations/glouton[/]', GeneriqueOptimisationAction::class);
    $app->post('/affectations/random[/]', GeneriqueOptimisationAction::class);

    /*************************
    * Routes de l'API
    *************************/
    $app->get('/besoins[/]', GeneriqueGestionAction::class);
    $app->get('/users/besoins[/]', GeneriqueGestionAction::class);
    $app->post('/besoins[/]', GeneriqueGestionAction::class);
    $app->put('/besoins/{id}[/]', GeneriqueGestionAction::class);

    // Salaries routes
    $app->get('/salaries[/]', GeneriqueGestionAction::class);

    // Competences routes
    $app->get('/competences/clients[/]', GeneriqueGestionAction::class);
    $app->get('/competences[/]', GeneriqueGestionAction::class);
    $app->get('/competences/{id}[/]', GeneriqueGestionAction::class);
    $app->post('/competences[/]', GeneriqueGestionAction::class);
    $app->put('/competences/{id}[/]', GeneriqueGestionAction::class);
    $app->delete('/competences/{id}[/]', GeneriqueGestionAction::class);

    // Utilisateur routes
    $app->post('/utilisateur[/]', GeneriqueGestionAction::class);

    /*************************
    * Routes de l'API Auth
    *************************/

    $app->post('/signin[/]', GeneriqueAuthAction::class)
        ->setName('tokenSignin');

    $app->post('/register[/]', GeneriqueAuthAction::class)
        ->setName('tokenRegister');

    return $app;
};
