import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './classification.reducer';
import { IClassification } from 'app/shared/model/classification.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClassificationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassificationUpdate = (props: IClassificationUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { classificationEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/classification');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...classificationEntity,
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
          <h2 id="jhipsterSampleMongoApp.classification.home.createOrEditLabel">
            <Translate contentKey="jhipsterSampleMongoApp.classification.home.createOrEditLabel">Create or edit a Classification</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : classificationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="classification-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="classification-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="classAtt1Label" for="classification-classAtt1">
                  <Translate contentKey="jhipsterSampleMongoApp.classification.classAtt1">Class Att 1</Translate>
                </Label>
                <AvField id="classification-classAtt1" type="text" name="classAtt1" />
              </AvGroup>
              <AvGroup>
                <Label id="classAtt2Label" for="classification-classAtt2">
                  <Translate contentKey="jhipsterSampleMongoApp.classification.classAtt2">Class Att 2</Translate>
                </Label>
                <AvField id="classification-classAtt2" type="text" name="classAtt2" />
              </AvGroup>
              <AvGroup>
                <Label id="classAtt3Label" for="classification-classAtt3">
                  <Translate contentKey="jhipsterSampleMongoApp.classification.classAtt3">Class Att 3</Translate>
                </Label>
                <AvField id="classification-classAtt3" type="text" name="classAtt3" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/classification" replace color="info">
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
  classificationEntity: storeState.classification.entity,
  loading: storeState.classification.loading,
  updating: storeState.classification.updating,
  updateSuccess: storeState.classification.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassificationUpdate);
