<?php

namespace gestion\application\actions;

use gestion\core\domain\entities\InputBesoinDTO;
use gestion\core\dto\InputCompetenceDTO;
use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PostCompetencesAction extends AbstractAction
{
    private GestionServiceInterface $gestionService;

    public function __construct(GestionServiceInterface $gestionService)
    {
        $this->gestionService = $gestionService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $body = $rq->getParsedBody();

        $inputCompetenceDTO = new InputCompetenceDTO($body['name'], $body['description']);

        $this->gestionService->createCompetence($inputCompetenceDTO);

        return $rs->withHeader('Content-Type', 'application/json')->withStatus(200);
    }
}