import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPropertySet } from 'app/shared/model/property-set.model';
import { getEntities as getPropertySets } from 'app/entities/property-set/property-set.reducer';
import { getEntity, updateEntity, createEntity, reset } from './property.reducer';
import { IProperty } from 'app/shared/model/property.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPropertyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PropertyUpdate = (props: IPropertyUpdateProps) => {
  const [propertySetId, setPropertySetId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { propertyEntity, propertySets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/property');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPropertySets();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...propertyEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleMongoApp.property.home.createOrEditLabel">
            <Translate contentKey="jhipsterSampleMongoApp.property.home.createOrEditLabel">Create or edit a Property</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : propertyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="property-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="property-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="property-name">
                  <Translate contentKey="jhipsterSampleMongoApp.property.name">Name</Translate>
                </Label>
                <AvField id="property-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="valueLabel" for="property-value">
                  <Translate contentKey="jhipsterSampleMongoApp.property.value">Value</Translate>
                </Label>
                <AvField id="property-value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label for="property-propertySet">
                  <Translate contentKey="jhipsterSampleMongoApp.property.propertySet">Property Set</Translate>
                </Label>
                <AvInput id="property-propertySet" type="select" className="form-control" name="propertySetId">
                  <option value="" key="0" />
                  {propertySets
                    ? propertySets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/property" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  propertySets: storeState.propertySet.entities,
  propertyEntity: storeState.property.entity,
  loading: storeState.property.loading,
  updating: storeState.property.updating,
  updateSuccess: storeState.property.updateSuccess
});

const mapDispatchToProps = {
  getPropertySets,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PropertyUpdate);
