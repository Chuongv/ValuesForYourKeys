
enum NonValue {
   KEY_NOT_FOUND = 1
}

struct StringData {
  1: required string data
}

union Response {
 1: NonValue   nonFound,
 2: StringData dataFound
}

service KeyValueService {
  Response getValue(1: string key),
  void     setValue(1: string key, 2: string data)
}