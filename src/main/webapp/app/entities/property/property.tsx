import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './property.reducer';
import { IProperty } from 'app/shared/model/property.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPropertyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Property = (props: IPropertyProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { propertyList, match, loading } = props;
  return (
    <div>
      <h2 id="property-heading">
        <Translate contentKey="jhipsterSampleMongoApp.property.home.title">Properties</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="jhipsterSampleMongoApp.property.home.createLabel">Create new Property</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {propertyList && propertyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleMongoApp.property.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleMongoApp.property.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleMongoApp.property.propertySet">Property Set</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {propertyList.map((property, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${property.id}`} color="link" size="sm">
                      {property.id}
                    </Button>
                  </td>
                  <td>{property.name}</td>
                  <td>{property.value}</td>
                  <td>
                    {property.propertySetId ? <Link to={`property-set/${property.propertySetId}`}>{property.propertySetId}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${property.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${property.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${property.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="jhipsterSampleMongoApp.property.home.notFound">No Properties found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ property }: IRootState) => ({
  propertyList: property.entities,
  loading: property.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Property);
