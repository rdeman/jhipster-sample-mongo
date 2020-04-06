import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './property.reducer';
import { IProperty } from 'app/shared/model/property.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPropertyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PropertyDetail = (props: IPropertyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { propertyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jhipsterSampleMongoApp.property.detail.title">Property</Translate> [<b>{propertyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterSampleMongoApp.property.name">Name</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.name}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="jhipsterSampleMongoApp.property.value">Value</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.value}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleMongoApp.property.propertySet">Property Set</Translate>
          </dt>
          <dd>{propertyEntity.propertySetId ? propertyEntity.propertySetId : ''}</dd>
        </dl>
        <Button tag={Link} to="/property" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/property/${propertyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ property }: IRootState) => ({
  propertyEntity: property.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PropertyDetail);
