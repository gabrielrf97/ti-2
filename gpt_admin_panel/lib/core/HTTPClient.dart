import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

class HTTPClient {
  final String _path;
  final String _baseURL = "http://localhost:8080";

  HTTPClient(this._path);

  Future<http.Response> get() async {
    return await http.get(
      Uri.parse(_baseURL + _path),
    );
  }

  Future<http.Response> post(Map<String, String> body) async {
    final uri = Uri.parse(_baseURL + _path);
    final jsonString = json.encode(body);
    final headers = {HttpHeaders.contentTypeHeader: 'application/json'};
    final response = await http.post(uri, headers: headers, body: jsonString);
    return response;
  }
}
