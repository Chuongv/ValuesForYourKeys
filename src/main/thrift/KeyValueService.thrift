
enum NonValue {
   KEY_NOT_FOUND = 1;
}

struct BinaryData {
  1: required binary data;
}

union Response {
 1: NonValue nonFound;
 2: BinaryData   dataFound;
}


service KeyValueService {
  Response getValue(1: string key);
  void   setValue(1: string key, binary data);
}