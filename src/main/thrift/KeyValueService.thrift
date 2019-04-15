
enum NonValue {
   KEY_NOT_FOUND = 1;
}

union Response {
 1: NonValue nonValue;
 2: binary   data;
}


service KeyValueService {
  Response getValue(1: string key);
  void   setValue(1: string key, binary data);
}