import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Classification from './classification';
import ClassificationDetail from './classification-detail';
import ClassificationUpdate from './classification-update';
import ClassificationDeleteDialog from './classification-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClassificationDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClassificationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClassificationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClassificationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Classification} />
    </Switch>
  </>
);

export default Routes;
