import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './classification.reducer';
import { IClassification } from 'app/shared/model/classification.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClassificationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassificationDetail = (props: IClassificationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { classificationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jhipsterSampleMongoApp.classification.detail.title">Classification</Translate> [
          <b>{classificationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="classAtt1">
              <Translate contentKey="jhipsterSampleMongoApp.classification.classAtt1">Class Att 1</Translate>
            </span>
          </dt>
          <dd>{classificationEntity.classAtt1}</dd>
          <dt>
            <span id="classAtt2">
              <Translate contentKey="jhipsterSampleMongoApp.classification.classAtt2">Class Att 2</Translate>
            </span>
          </dt>
          <dd>{classificationEntity.classAtt2}</dd>
          <dt>
            <span id="classAtt3">
              <Translate contentKey="jhipsterSampleMongoApp.classification.classAtt3">Class Att 3</Translate>
            </span>
          </dt>
          <dd>{classificationEntity.classAtt3}</dd>
        </dl>
        <Button tag={Link} to="/classification" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/classification/${classificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ classification }: IRootState) => ({
  classificationEntity: classification.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassificationDetail);
