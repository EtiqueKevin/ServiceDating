<?php


use gestion\application\actions\GetBesoinsAdminAction;
use gestion\application\actions\GetBesoinsByUserAction;
use gestion\application\actions\PostBesoinAction;
use gestion\application\actions\PostUtilisateurAction;
use gestion\core\repositoryInterface\GestionRepositoryInterface;
use gestion\core\services\GestionService;
use gestion\core\services\GestionServiceInterface;
use gestion\infrastructure\repository\PDOGestionRepository;
use Psr\Container\ContainerInterface;

return [

    'pdo' => function(ContainerInterface $container) {
        $config = parse_ini_file('iniconf/gestion.ini');
        $dsn = "{$config['driver']}:host={$config['host']};port={$config['port']};dbname={$config['database']};";
        $user = $config['username'];
        $password = $config['password'];
        return new \PDO($dsn, $user, $password, [\PDO::ATTR_ERRMODE => \PDO::ERRMODE_EXCEPTION]);
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

    GestionServiceInterface::class => function(ContainerInterface $container) {
        return new GestionService($container->get(GestionRepositoryInterface::class));
    },

    GestionRepositoryInterface::class => function(ContainerInterface $container) {
        return new PDOGestionRepository($container->get('pdo'));
    }
];