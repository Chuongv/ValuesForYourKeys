
enum NonValue {
   KEY_NOT_FOUND = 1;
}

struct BinaryData {
  1: required binary data;
}

union Response {
 1: NonValue nonValue;
 2: BinaryData   binaryData;
}


service KeyValueService {
  Response getValue(1: string key);
  void   setValue(1: string key, binary data);
}