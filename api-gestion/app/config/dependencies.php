<?php


use gestion\application\actions\GetBesoinsAdminAction;
use gestion\application\actions\GetBesoinsByUserAction;
use gestion\application\actions\PostBesoinAction;
use gestion\application\actions\PostUtilisateurAction;
use gestion\application\middlewares\AuthMiddleware;
use gestion\core\repositoryInterface\AuthRepositoryInterface;

use gestion\application\actions\GetCompetencesAction;
use gestion\application\actions\GetCompetencesByIdAction;
use gestion\application\actions\PostCompetencesAction;
use gestion\application\actions\PutCompetencesAction;
use gestion\application\actions\DeleteCompetencesAction;

use gestion\core\repositoryInterface\GestionRepositoryInterface;
use gestion\core\services\GestionService;
use gestion\core\services\GestionServiceInterface;
use gestion\infrastructure\repository\PDOGestionRepository;
use gestion\infrastructure\adaptater\AdaptaterAuthRepository;
use Psr\Container\ContainerInterface;

return [

    'pdo' => function(ContainerInterface $container) {
        $config = parse_ini_file('iniconf/gestion.ini');
        $dsn = "{$config['driver']}:host={$config['host']};port={$config['port']};dbname={$config['database']};";
        $user = $config['username'];
        $password = $config['password'];
        return new \PDO($dsn, $user, $password, [\PDO::ATTR_ERRMODE => \PDO::ERRMODE_EXCEPTION]);
    },

    AuthRepositoryInterface::class => function(ContainerInterface $container) {
        return new AdaptaterAuthRepository($container->get('client_auth'));
    },

    GetBesoinsAdminAction::class => function(ContainerInterface $container) {
        return new GetBesoinsAdminAction($container->get(GestionServiceInterface::class));
    },

    GetBesoinsByUserAction::class => function(ContainerInterface $container) {
        return new GetBesoinsByUserAction($container->get(GestionServiceInterface::class));
    },

    PostBesoinAction::class => function(ContainerInterface $container) {
        return new PostBesoinAction($container->get(GestionServiceInterface::class));
    },

    PostUtilisateurAction::class => function(ContainerInterface $container) {
        return new PostUtilisateurAction($container->get(GestionServiceInterface::class));
    },

    GetCompetencesAction::class => function(ContainerInterface $container) {
        return new GetCompetencesAction($container->get(GestionServiceInterface::class));
    },

    GetCompetencesByIdAction::class => function(ContainerInterface $container) {
        return new GetCompetencesByIdAction($container->get(GestionServiceInterface::class));
    },

    PostCompetencesAction::class => function(ContainerInterface $container) {
        return new PostCompetencesAction($container->get(GestionServiceInterface::class));
    },

    PutCompetencesAction::class => function(ContainerInterface $container) {
        return new PutCompetencesAction($container->get(GestionServiceInterface::class));
    },

    DeleteCompetencesAction::class => function(ContainerInterface $container) {
        return new DeleteCompetencesAction($container->get(GestionServiceInterface::class));
    },

    GestionServiceInterface::class => function(ContainerInterface $container) {
        return new GestionService($container->get(GestionRepositoryInterface::class), $container->get(AuthRepositoryInterface::class));
    },

    GestionRepositoryInterface::class => function(ContainerInterface $container) {
        return new PDOGestionRepository($container->get('pdo'));
    },

    AuthMiddleware::class => function(ContainerInterface $container) {
        return new AuthMiddleware($container->get(GestionServiceInterface::class));
    }
];