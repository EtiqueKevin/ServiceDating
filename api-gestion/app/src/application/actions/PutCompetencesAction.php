<?php

namespace gestion\application\actions;

use gestion\core\domain\entities\InputBesoinDTO;
use gestion\core\dto\InputCompetenceDTO;
use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PutCompetencesAction extends AbstractAction
{
    private GestionServiceInterface $gestionService;

    public function __construct(GestionServiceInterface $gestionService)
    {
        $this->gestionService = $gestionService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $competenceId = $args['id'];
        $body = $rq->getParsedBody();

        $inputCompetenceDTO = new InputCompetenceDTO($body['name'], $body['description']);

        $inputCompetenceDTO->setId($competenceId);

        $this->gestionService->updateCompetence($inputCompetenceDTO);

        return $rs->withHeader('Content-Type', 'application/json')->withStatus(201);
    }
}