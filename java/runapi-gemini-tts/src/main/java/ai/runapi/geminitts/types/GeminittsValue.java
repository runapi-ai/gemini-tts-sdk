package ai.runapi.geminitts.types;

import ai.runapi.core.types.RunApiValue;

abstract class GeminittsValue extends RunApiValue {
  GeminittsValue(String value) {
    super(value);
  }
}
