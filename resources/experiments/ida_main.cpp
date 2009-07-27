#include "_Experiment.hh"
#include "_IDASolver.hh"

#include "_selector.h"

#include "eModelicaExperimentDefinitions.h"

int main (int argc, char** argv) {
	_parse_opts(&argc, argv);
	_Experiment *ex = new _Experiment(); // monolithisches Experiment.
   	ex->init(
	_selectRootObject(ROOT_OBJECT),OUTPUT_VARS);
   	
	ex->setSolver( new _IDASolver(TIME_START, TIME_END, MIN_STEP, MAX_STEP) );
	ex->rt->solver->setup->tol = RELATIVE_TOLERANCE;
	ex->rt->solver->setup->aTol = ABSOLUTE_TOLERANCE;
   	ex->start();
	delete ex;
   	exit(0);
}
