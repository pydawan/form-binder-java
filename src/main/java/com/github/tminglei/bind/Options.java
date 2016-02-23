package com.github.tminglei.bind;

import java.util.*;
import java.util.stream.Collectors;

import com.github.tminglei.bind.spi.*;

import static com.github.tminglei.bind.FrameworkUtils.*;

/**
 * object used to hold options and internal status
 */
public class Options {
    public static final Options EMPTY = new Options();

    private Boolean eagerCheck;
    private Boolean ignoreEmpty;
    private TouchedChecker touched;
    // internal state, only applied to current mapping
    private InputMode _inputMode;
    private String  _label = null;
    private boolean _ignoreConstraints = false;
    private List<Constraint> _constraints = Collections.EMPTY_LIST;
    private List<ExtraConstraint<?>> _extraConstraints = Collections.EMPTY_LIST;
    private List<PreProcessor> _processors = Collections.EMPTY_LIST;
    // extension object
    private Extensible _extData;

    public Options() {}
    public Options(Boolean eagerCheck, Boolean ignoreEmpty, TouchedChecker touched) {
        this.eagerCheck = eagerCheck;
        this.ignoreEmpty = ignoreEmpty;
        this.touched = touched;
    }

    public Options merge(Options other) {
        Options clone = this.clone();
        clone.eagerCheck = eagerCheck != null ? eagerCheck : other.eagerCheck;
        clone.ignoreEmpty = ignoreEmpty != null ? ignoreEmpty : other.ignoreEmpty;
        clone.touched = touched != null ? touched : other.touched;
        return clone;
    }

    ///

    /**
     * whether check errors as more as possible
     * @return the value optional
     */
    public Optional<Boolean> eagerCheck() {
        return Optional.ofNullable(this.eagerCheck);
    }
    public Options eagerCheck(Boolean eagerCheck) {
        Options clone = this.clone();
        clone.eagerCheck = eagerCheck;
        return clone;
    }

    /**
     * whether skip to check empty even they are declared as required
     * @return the value optional
     */
    public Optional<Boolean> ignoreEmpty() {
        return Optional.ofNullable(this.ignoreEmpty);
    }
    public Options ignoreEmpty(Boolean ignoreEmpty) {
        Options clone = this.clone();
        clone.ignoreEmpty = ignoreEmpty;
        return clone;
    }

    /**
     * for touched fields, an error will be filed if they are declared as required and they are empty
     * @return the touched checker
     */
    public TouchedChecker touched() {
        return this.touched;
    }
    public Options touched(TouchedChecker touched) {
        Options clone = this.clone();
        clone.touched = touched;
        return clone;
    }

    //-- internal options
    InputMode _inputMode() {
        return this._inputMode;
    }
    Options _inputMode(InputMode inputMode) {
        Options clone = this.clone();
        clone._inputMode = inputMode;
        return clone;
    }

    Optional<String> _label() {
        return Optional.ofNullable(this._label);
    }
    Options _label(String label) {
        Options clone = this.clone();
        clone._label = label;
        return clone;
    }

    boolean _ignoreConstraints() {
        return this._ignoreConstraints;
    }
    Options _ignoreConstraints(boolean ignore) {
        Options clone = this.clone();
        clone._ignoreConstraints = ignore;
        return clone;
    }

    List<Constraint> _constraints() {
        return this._constraints;
    }
    Options _constraints(List<Constraint> constraints) {
        Options clone = this.clone();
        clone._constraints = unmodifiableList(constraints);
        return clone;
    }
    Options append_constraints(List<Constraint> constraints) {
        Options clone = this.clone();
        clone._constraints = unmodifiableList(mergeList(clone._constraints, constraints));
        return clone;
    }
    Options prepend_constraints(List<Constraint> constraints) {
        Options clone = this.clone();
        clone._constraints = unmodifiableList(mergeList(constraints, clone._constraints));
        return clone;
    }

    List<PreProcessor> _processors() {
        return this._processors;
    }
    Options _processors(List<PreProcessor> processors) {
        Options clone = this.clone();
        clone._processors = unmodifiableList(processors);
        return clone;
    }
    Options append_processors(List<PreProcessor> processors) {
        Options clone = this.clone();
        clone._processors = unmodifiableList(mergeList(clone._processors, processors));
        return clone;
    }
    Options prepend_processors(List<PreProcessor> processors) {
        Options clone = this.clone();
        clone._processors = unmodifiableList(mergeList(processors, clone._processors));
        return clone;
    }

    <T> List<ExtraConstraint<T>> _extraConstraints() {
        return this._extraConstraints.stream().map(c -> (ExtraConstraint<T>) c).collect(Collectors.toList());
    }
    Options _extraConstraints(List<ExtraConstraint<?>> extraConstraints) {
        Options clone = this.clone();
        clone._extraConstraints = unmodifiableList(extraConstraints);
        return clone;
    }
    Options append_extraConstraints(List<ExtraConstraint<?>> extraConstraints) {
        Options clone = this.clone();
        clone._extraConstraints = unmodifiableList(mergeList(clone._extraConstraints, extraConstraints));
        return clone;
    }

    ///
    Extensible _extData() {
        return this._extData;
    }
    Options _extData(Extensible ext) {
        Options clone = this.clone();
        clone._extData = ext;
        return clone;
    }

    /////////////////////////////////////////////////////////////////////////////////////

    protected Options clone() {
        Options clone = new Options(this.eagerCheck, this.ignoreEmpty, this.touched);
        clone._inputMode = this._inputMode;
        clone._label = this._label;
        clone._ignoreConstraints = this._ignoreConstraints;
        clone._constraints = this._constraints;
        clone._extraConstraints = this._extraConstraints;
        clone._processors = this._processors;
        clone._extData = this._extData;
        return clone;
    }
}
