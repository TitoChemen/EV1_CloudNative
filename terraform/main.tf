resource "aws_instance" "microservicios_server" {
  ami           = "ami-04e914639d0437a16"
  instance_type = "t2.medium"

  tags = {
    Name = "Servidor-Microservicios-CloudNative"
  }
}