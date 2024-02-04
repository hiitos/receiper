FROM python:3.11
WORKDIR /workspace
COPY ./webhook/functions/requirements.txt .

RUN pip install --upgrade pip
RUN pip3 install functions-framework
RUN pip3 install -r requirements.txt