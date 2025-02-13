<?php

namespace gestion\application\actions;

use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class DeleteCompetencesAction extends AbstractAction
{
    private GestionServiceInterface $gestionService;

    public function __construct(GestionServiceInterface $gestionService)
    {
        $this->gestionService = $gestionService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $competenceId = $args['id'];

        $this->gestionService->deleteCompetence($competenceId);

        return $rs->withHeader('Content-Type', 'application/json')->withStatus(201);
    }
}