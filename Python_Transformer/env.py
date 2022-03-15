from flask import Flask, request, jsonify
import trans_predict

app = Flask(__name__)
app.debug = True


@app.route('/')
def get_comment():
    # Return the result of trained Transformer
    grt = trans_predict.main()
    input = [99, 77, 11, 22]
    result = grt(input)
    # result = grt.resultDict
    print(result)
    return jsonify(result)


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=1234)
