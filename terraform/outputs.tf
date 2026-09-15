output "instance_public_ip" {
  value = aws_instance.app_server.public_ip
}

output "ssh_command" {
  value = "ssh -i ec2-key.pem ubuntu@${aws_instance.app_server.public_ip}"
}