import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './property-set.reducer';
import { IPropertySet } from 'app/shared/model/property-set.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPropertySetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const PropertySet = (props: IPropertySetProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { propertySetList, match, loading } = props;
  return (
    <div>
      <h2 id="property-set-heading">
        <Translate contentKey="jhipsterSampleMongoApp.propertySet.home.title">Property Sets</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="jhipsterSampleMongoApp.propertySet.home.createLabel">Create new Property Set</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {propertySetList && propertySetList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleMongoApp.propertySet.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleMongoApp.propertySet.data">Data</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {propertySetList.map((propertySet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${propertySet.id}`} color="link" size="sm">
                      {propertySet.id}
                    </Button>
                  </td>
                  <td>{propertySet.name}</td>
                  <td>{propertySet.dataId ? <Link to={`data/${propertySet.dataId}`}>{propertySet.dataId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${propertySet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${propertySet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${propertySet.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleMongoApp.propertySet.home.notFound">No Property Sets found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ propertySet }: IRootState) => ({
  propertySetList: propertySet.entities,
  loading: propertySet.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PropertySet);
