#!/usr/bin/env ruby
require 'bunny'

# connection = Bunny.new
connection = Bunny.new(hostname: 'rdp02.unizar.es', user: 'alumno', pass: 'alumno')
connection.start

channel = connection.create_channel
exchange = channel.direct('as2')
# severity = ARGV.shift || 'info'
severity = 'vv'
message = ARGV.empty? ? 'Hello World!' : ARGV.join(' ')

exchange.publish(message, routing_key: severity)
puts " [x] Sent '#{message}'"

connection.close